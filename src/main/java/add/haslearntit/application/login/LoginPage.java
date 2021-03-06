package add.haslearntit.application.login;

import org.apache.wicket.spring.injection.annot.SpringBean;

import add.haslearntit.application.TemplatePage;
import add.haslearntit.domain.user.UserRepository;

public class LoginPage extends TemplatePage {

    @SpringBean
    private UserRepository userRepository;
    
    public LoginPage() {

        add(new LoginForm(userRepository));

    }

}
