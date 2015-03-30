package it.redhat.playground.visualizer.dto;

public class JDGNodeInfo {

    private String label;
    private Long value;

    public JDGNodeInfo() {
    }

    public JDGNodeInfo(String label, Long value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
