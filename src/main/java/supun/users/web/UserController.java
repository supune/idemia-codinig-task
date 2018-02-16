package supun.users.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import supun.users.data.User;
import supun.users.data.UserRepository;

import java.util.List;


@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView index() {

        ModelAndView mav = new ModelAndView("index");

        List<User> users = userRepository.findAll();
        mav.addObject("users", users);

        return mav;
    }

    @RequestMapping(value = "/userForm", method = RequestMethod.GET)
    public ModelAndView userForm() {

       return new ModelAndView("userForm");

    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findUser(@PathVariable("id") long id) {
        User found = userRepository.findOne(id);
        if(found == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(found);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @RequestMapping(value = "/users/json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> listUsers() {

        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}
