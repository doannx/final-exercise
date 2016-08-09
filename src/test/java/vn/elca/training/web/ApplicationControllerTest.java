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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationLauncher.class)
@WebAppConfiguration
public class ApplicationControllerTest {
    private MockMvc mockMvc;
    @Autowired
    ApplicationController app;

    @Test
    public void testMutilingual() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(app).build();
        RequestBuilder changeLang = MockMvcRequestBuilders.get("/fr");
        mockMvc.perform(changeLang).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("search"));
    }
}
