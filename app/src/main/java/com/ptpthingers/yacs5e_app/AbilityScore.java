package com.ptpthingers.yacs5e_app;

class AbilityScore {
    private Integer value;
    private Integer modifier;
    private Integer tempValue;
    private Integer tempModifier;
    private Boolean useTemp;

    public AbilityScore(Integer value) {
        setValue(value);
        setTempValue(value);
        this.useTemp = Boolean.FALSE;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
        this.modifier = ((value-10)/2);
    }

    public Integer getModifier() {
        return modifier;
    }

    public Integer getTempValue() {
        return tempValue;
    }

    public void setTempValue(Integer tempValue) {
        this.tempValue = tempValue;
        this.tempModifier = ((tempValue-10)/2);
    }

    public Integer getTempModifier() {
        return tempModifier;
    }

    public Boolean getUseTemp() {
        return useTemp;
    }

    public void setUseTemp(Boolean useTemp) {
        this.useTemp = useTemp;
    }
}
