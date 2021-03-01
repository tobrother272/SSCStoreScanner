/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormComponent;

/**
 *
 * @author PC
 */
public class SSCFormFormat {
    private String value;
    private int type;
    private String label;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SSCFormFormat(String value, int type, String label) {
        this.value = value;
        this.type = type;
        this.label = label;
    }
    
}
