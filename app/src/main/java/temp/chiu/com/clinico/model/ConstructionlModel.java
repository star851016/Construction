package temp.chiu.com.clinico.model;

/**
 * Created by xin-ling on 2016/5/15.
 */
public class ConstructionlModel {
    public String _id;
    public String APP_NAME;
    public String C_NAME;
    public String ADDR;
    public String CB_DA;
    public String CE_DA;
    public String CO_TI;
    public String TC_MA;
    public String TC_TL;
    public String TC_MA3;
    public String TC_TL3;
    public String NPURP;
    public String X;
    public String Y;



    public String getAPP_NAME() {
        return APP_NAME == null ? "" :APP_NAME;
    }

    public String get_id() {return _id == null ? "" :  _id;}

    public String getC_NAME() {return C_NAME == null ? "" : C_NAME;}

    public String getADDR() {
        return ADDR == null ? "" : ADDR;
    }

    public String getCB_DA() {return CB_DA == null ? "" : CB_DA;}

    public String getCE_DA() {
        return CE_DA == null ? "" : CE_DA;
    }

    public String getCO_TI() {
        return CO_TI == null ? "" : CO_TI;
    }

    public String getTC_MA() {
        return TC_MA == null ? "" : TC_MA;
    }

    public String getTC_TL() {
        return TC_TL == null ? "" : TC_TL;
    }

    public String getTC_MA3() {
        return TC_MA3 == null ? "" : TC_MA3;
    }

    public String getTC_TL3() {
        return TC_TL3 == null ? "" :TC_TL3;
    }

    public String getNPURP() {return NPURP == null ? "" : NPURP;}

    public String getX() {return X == null ? "" : X;}

    public String getY() {return Y == null ? "" : Y;}
}
