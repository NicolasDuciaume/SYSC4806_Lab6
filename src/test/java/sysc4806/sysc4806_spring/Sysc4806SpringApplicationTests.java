package sysc4806.sysc4806_spring;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
class Sysc4806SpringApplicationTests {

    @Autowired
    private MainController controller;

    @Test
    public void contextLoads() throws Exception {
        assert(controller != null);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void RedirectsAddressBook() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(redirectedUrl("/AddressBookMenu"));
    }

    @Test
    public void RedirectsAdd() throws Exception {
        this.mockMvc.perform(post("/AddressBookAddBuddy")).andExpect(redirectedUrl("/AddressBookAddBuddy"));
    }
}
