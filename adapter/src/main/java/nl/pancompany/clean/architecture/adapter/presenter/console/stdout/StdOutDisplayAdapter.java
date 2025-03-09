package nl.pancompany.clean.architecture.adapter.presenter.console.stdout;

import lombok.extern.slf4j.Slf4j;
import nl.pancompany.clean.architecture.common.annotation.architecture.Adapter;
import nl.pancompany.clean.architecture.application.port.out.DummyDisplayPort;

@Slf4j
@Adapter("std-out-display-adapter")
public class StdOutDisplayAdapter implements DummyDisplayPort {

    @Override
    public void display(String data) {
        System.out.println("Data: " + data);
    }
}