package nl.pancompany.clean.architecture.it.support.fakeadapters;

import lombok.Getter;
import nl.pancompany.clean.architecture.application.port.out.DummyDisplayPort;

public class DummyDataSink implements DummyDisplayPort {

    @Getter
    private String data;

    @Override
    public void display(String data) {
        this.data = data;
    }
}