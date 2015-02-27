package it.redhat.playground.bean;

/**
 * Created by samuele on 2/26/15.
 */
public class JdgEntry {

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private Long key;
    private String value;
}
