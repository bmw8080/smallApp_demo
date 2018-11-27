package json.model;


import java.util.Date;

public class Invoice {

    private Integer xyId;

    private String xyInvoiceCode;

    private String xyInvoiceNum;

    private String xyInvoiceFlownum;

    private Float xyInvoiceCash;

    private Float xyInvoiceTax;

    private Float xyInvoiceTotal;

    private String xyInvoiceType;

    private String xyNote;

    private String xyBuyername;

    private String xyBuyertaxcode;

    private String xyBuyerbankAccount;

    private String xyBuyertel;

    private String xySalertaxcode;

    private String xySalername;

    private String xySalertel;

    private String xySalerbankaccount;

    private String xyOdate;

    private String xyV;

    private String xyR;

    private String xyPeople;

    private String xyRdate;

    private String xyInputDate;

    public Integer getXyId(Integer xy_id) {
        return xyId;
    }

    public void setXyId(Integer xyId) {
        this.xyId = xyId;
    }

    public String getXyInvoiceCode() {
        return xyInvoiceCode;
    }

    public void setXyInvoiceCode(String xyInvoiceCode) {
        this.xyInvoiceCode = xyInvoiceCode == null ? null : xyInvoiceCode.trim();
    }

    public String getXyInvoiceNum() {
        return xyInvoiceNum;
    }

    public void setXyInvoiceNum(String xyInvoiceNum) {
        this.xyInvoiceNum = xyInvoiceNum == null ? null : xyInvoiceNum.trim();
    }

    public String getXyInvoiceFlownum() {
        return xyInvoiceFlownum;
    }

    public void setXyInvoiceFlownum(String xyInvoiceFlownum) {
        this.xyInvoiceFlownum = xyInvoiceFlownum == null ? null : xyInvoiceFlownum.trim();
    }

    public Float getXyInvoiceCash() {
        return xyInvoiceCash;
    }

    public void setXyInvoiceCash(Float xyInvoiceCash) {
        this.xyInvoiceCash = xyInvoiceCash;
    }

    public Float getXyInvoiceTax() {
        return xyInvoiceTax;
    }

    public void setXyInvoiceTax(Float xyInvoiceTax) {
        this.xyInvoiceTax = xyInvoiceTax;
    }

    public Float getXyInvoiceTotal() {
        return xyInvoiceTotal;
    }

    public void setXyInvoiceTotal(Float xyInvoiceTotal) {
        this.xyInvoiceTotal = xyInvoiceTotal;
    }

    public String getXyInvoiceType() {
        return xyInvoiceType;
    }

    public void setXyInvoiceType(String xyInvoiceType) {
        this.xyInvoiceType = xyInvoiceType == null ? null : xyInvoiceType.trim();
    }

    public String getXyNote() {
        return xyNote;
    }

    public void setXyNote(String xyNote) {
        this.xyNote = xyNote == null ? null : xyNote.trim();
    }

    public String getXyBuyername() {
        return xyBuyername;
    }

    public void setXyBuyername(String xyBuyername) {
        this.xyBuyername = xyBuyername == null ? null : xyBuyername.trim();
    }

    public String getXyBuyertaxcode() {
        return xyBuyertaxcode;
    }

    public void setXyBuyertaxcode(String xyBuyertaxcode) {
        this.xyBuyertaxcode = xyBuyertaxcode == null ? null : xyBuyertaxcode.trim();
    }

    public String getXyBuyerbankAccount() {
        return xyBuyerbankAccount;
    }

    public void setXyBuyerbankAccount(String xyBuyerbankAccount) {
        this.xyBuyerbankAccount = xyBuyerbankAccount == null ? null : xyBuyerbankAccount.trim();
    }

    public String getXyBuyertel() {
        return xyBuyertel;
    }

    public void setXyBuyertel(String xyBuyertel) {
        this.xyBuyertel = xyBuyertel == null ? null : xyBuyertel.trim();
    }

    public String getXySalertaxcode() {
        return xySalertaxcode;
    }

    public void setXySalertaxcode(String xySalertaxcode) {
        this.xySalertaxcode = xySalertaxcode == null ? null : xySalertaxcode.trim();
    }

    public String getXySalername() {
        return xySalername;
    }

    public void setXySalername(String xySalername) {
        this.xySalername = xySalername == null ? null : xySalername.trim();
    }

    public String getXySalertel() {
        return xySalertel;
    }

    public void setXySalertel(String xySalertel) {
        this.xySalertel = xySalertel == null ? null : xySalertel.trim();
    }

    public String getXySalerbankaccount() {
        return xySalerbankaccount;
    }

    public void setXySalerbankaccount(String xySalerbankaccount) {
        this.xySalerbankaccount = xySalerbankaccount == null ? null : xySalerbankaccount.trim();
    }

    public String getXyOdate() {
        return xyOdate;
    }

    public void setXyOdate(String xyOdate) {
        this.xyOdate = xyOdate == null ? null : xyOdate.trim();
    }

    public String getXyV() {
        return xyV;
    }

    public void setXyV(String xyV) {
        this.xyV = xyV == null ? null : xyV.trim();
    }

    public String getXyR() {
        return xyR;
    }

    public void setXyR(String xyR) {
        this.xyR = xyR == null ? null : xyR.trim();
    }

    public String getXyPeople() {
        return xyPeople;
    }

    public void setXyPeople(String xyPeople) {
        this.xyPeople = xyPeople == null ? null : xyPeople.trim();
    }

    public String getXyRdate() {
        return xyRdate;
    }

    public void setXyRdate(String xyRdate) {
        this.xyRdate = xyRdate == null ? null : xyRdate.trim();
    }

    public Integer getXyId() {
        return xyId;
    }

    public String getXyInputDate(){return xyInputDate;}

    public void setXyInputDate(String xyInputDate) {
        this.xyInputDate = xyInputDate == null ? null : xyInputDate.trim();
    }


}