package com.soa.ppd_planner.model;

public enum CountryEnum {

    AT("Austria"),
    BE("Belgio"),
    HR("Croazia"),
    CY("Cipro"),
    EE("Estonia"),
    FI("Finlandia"),
    FR("Francia"),
    DE("Germania"),
    EL("Grecia"),
    IE("Irlanda"),
    IT("Italia"),
    LV("Lettonia"),
    LT("Lituania"),
    LU("Lussemburgo"),
    MT("Malta"),
    NL("Paesi Bassi"),
    PT("Portogallo"),
    SK("Slovacchia"),
    SI("Slovenia"),
    ES("Spagna");

    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    CountryEnum(String label) {
        this.label = label;
    }
}
