package kz.ecc.isbp.admin.common.dto.xmlAdapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanYesNoXmlAdapter extends XmlAdapter<String, Boolean> {
    public Boolean unmarshal(String s) {
        return s == null ? null : s.equals("Y");
    }

    public String marshal(Boolean c) {
        return c == null ? null : c ? "Y" : "N";
    }
}