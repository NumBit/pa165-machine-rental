package cz.muni.fi.pa165.dmbk.machinerental.dao.user;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This enum lists possible types of customer:<br />
 * <b>INDIVIDUAL</b>  -> simple person customer.<br />
 * <b>CORPORATION</b> -> represents customer as corporation.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
public enum LegalForm {
    INDIVIDUAL, CORPORATION;

    @JsonCreator
    public static LegalForm fromText(String text){
        if (text.equals("INDIVIDUAL")) return LegalForm.INDIVIDUAL;
        if (text.equals("CORPORATION")) return LegalForm.CORPORATION;
        throw new IllegalArgumentException();
    }
}
