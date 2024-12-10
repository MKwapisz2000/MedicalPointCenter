package com.przychodnia.przychodnia_aplikacja.service;

import com.przychodnia.przychodnia_aplikacja.component.PasswordHasher;
import com.przychodnia.przychodnia_aplikacja.model.User;
import com.przychodnia.przychodnia_aplikacja.repository.*;
import org.springframework.stereotype.Service;

@Service
public class LogowanieService {

    private final UserRepository userRepository;
    private final PacjentRepository pacjentRepository;
    private final AdminRepository adminRepository;
    private final LekarzRepository lekarzRepository;
    private final PasswordHasher passwordHasher;

    public LogowanieService(UserRepository userRepository, PacjentRepository pacjentRepository, AdresRepository adresRepository, AdminRepository adminRepository, LekarzRepository lekarzRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.pacjentRepository = pacjentRepository;
        this.adminRepository = adminRepository;
        this.lekarzRepository = lekarzRepository;
        this.passwordHasher = passwordHasher;
    }

    public String logowanieUser(String email, String haslo) {
        //Sprawdzenie czy użytkownik o danym emailu jest w bazie
        if (!(userRepository.existsByEmail(email))) {
            throw new RuntimeException("Użytkownik o takim emailu nie istnieje!");
        }

        else{
            //Pobranie id usera
            Long IdUser = userRepository.getIDByEmail(email);

            //Pobranie zachaschowanego hasla z tabeli
            String Hash_Haslo_Table = userRepository.getHasloById(IdUser);

            if (!passwordHasher.verifyPassword(haslo, Hash_Haslo_Table)) {
                throw new RuntimeException("Podano będne hasło!");
            }


            else{
                //Pobranie statusu usera
                String statusString = userRepository.getStatusById(IdUser);
                User.Status status = User.Status.valueOf(statusString);


                //Sprawdzenie czy użytkownik jest aktywny
                if(status == User.Status.NIEAKTYWNY){
                    throw new RuntimeException("Użytkownik jest nieaktywny!");
                }

                else{
                    //Pobranie typu usera
                    String typString = userRepository.getTypById(IdUser);
                    User.TypUzytkownika typ = User.TypUzytkownika.valueOf(typString);
                    System.out.println("Użytkownik zalogowany jako: " + typ);

                    switch (typ) {
                        case ADMIN:
                            return "redirect:/admin/dashboard";
                        case LEKARZ:
                            return "redirect:/lekarz/dashboard";
                        case PACJENT:
                            return "redirect:/pacjent/dashboard";
                        default:
                            throw new RuntimeException("Nieznany typ użytkownika!");
                    }
                }
            }
        }

    }
}