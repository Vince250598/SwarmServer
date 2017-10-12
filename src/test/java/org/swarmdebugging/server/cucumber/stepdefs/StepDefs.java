package org.swarmdebugging.server.cucumber.stepdefs;

import org.swarmdebugging.server.SwarmServerApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = SwarmServerApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
