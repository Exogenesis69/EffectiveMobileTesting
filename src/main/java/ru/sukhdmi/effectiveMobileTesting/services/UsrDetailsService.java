package ru.sukhdmi.effectiveMobileTesting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sukhdmi.effectiveMobileTesting.exceptions.UserNotFoundException;
import ru.sukhdmi.effectiveMobileTesting.models.User;
import ru.sukhdmi.effectiveMobileTesting.repositories.UserRepository;
import ru.sukhdmi.effectiveMobileTesting.security.UsrDetails;

import java.util.Optional;

@Service
public class UsrDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UsrDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found!");
        return new UsrDetails(user.get());
    }

    public User getCurrentUser() throws UserNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UsrDetails) {
            username = ((UsrDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return getUserByUsername(username);
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }


    public User getUserByUsername(String username) throws UsernameNotFoundException, UserNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }


}
