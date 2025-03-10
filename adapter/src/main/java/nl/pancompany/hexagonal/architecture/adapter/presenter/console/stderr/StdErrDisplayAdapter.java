package nl.pancompany.hexagonal.architecture.adapter.presenter.console.stderr;

import lombok.extern.slf4j.Slf4j;
import nl.pancompany.hexagonal.architecture.common.annotation.architecture.Adapter;
import nl.pancompany.hexagonal.architecture.application.port.out.DummyDisplayPort;

@Slf4j
@Adapter("std-err-display-adapter")
public class StdErrDisplayAdapter implements DummyDisplayPort {

    @Override
    public void display(String data) {
        System.err.println("Data: " + data);
    }
}