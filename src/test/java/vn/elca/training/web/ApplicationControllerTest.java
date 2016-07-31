package vn.elca.training.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import vn.elca.training.ApplicationLauncher;
import vn.elca.training.dom.UserPreference;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationLauncher.class)
@WebAppConfiguration
public class ApplicationControllerTest {
    @Autowired
    ApplicationController app;
    @Autowired
    UserPreference pref;

    @Test
    public void testController() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(app).build();
        RequestBuilder getNewPet = MockMvcRequestBuilders.get("/query?name=S");
        mockMvc.perform(getNewPet).andExpect(MockMvcResultMatchers.status().isOk());
        // .andExpect(MockMvcResultMatchers.view().name("detail"))
        // .andExpect(MockMvcResultMatchers.model().attributeExists("projectList"));
        // ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationLauncher.class);
        // beanFactory.registerScope(WebApplicationContext.SCOPE_REQUEST, new RequestScope());
        // UserPreference pref = context.getBean(UserPreference.class);
        // Assert.assertNotNull(pref);
        // Assert.assertEquals("S", pref.getUserCriterion());
    }

    static int absorbeTheValue() {
        try {
            int a = 10 / 0;
            if (a > 0)
                return 4;
        } catch (Exception e) {
            return 45;
        } finally {
            return 34;
        }
    }

    public static void main(String[] args) {
        System.out.println(absorbeTheValue());
    }
}
