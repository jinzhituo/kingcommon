package com.zhuanbang.kingcommon.terminal;

import android.os.Parcel;
import android.os.Parcelable;

public class PosTerminal implements Parcelable {
    Long posTerminalId;
    Long shopStoreId;//所属门店 【第一次可空】 确定之后，不可更改  [可空]
    String storeName; //门店名[可空]
    String posTerminalNo;  //终端号 (网付终端号)
    String posTerminalSerialNo; //设备序列号 （硬件序列号）
    String posTerminalName; //设备名称
    String productName;        //设备产品名称
    String productImage;      //设备产品图片
    long posPackageTemplateId;  //套餐模板Id (new)
    long bindMoneyCodeCount;  //绑定的收钱码数量 (new)
    int isCanBindMoneyCode; //是否可绑定收钱码 (new)
    int isCanBindPosPackageTemplate; //是否可绑定收银套餐 (new)

    public long getPosPackageTemplateId() {
        return posPackageTemplateId;
    }

    public void setPosPackageTemplateId(long posPackageTemplateId) {
        this.posPackageTemplateId = posPackageTemplateId;
    }

    public long getBindMoneyCodeCount() {
        return bindMoneyCodeCount;
    }

    public void setBindMoneyCodeCount(long bindMoneyCodeCount) {
        this.bindMoneyCodeCount = bindMoneyCodeCount;
    }

    public int getIsCanBindMoneyCode() {
        return isCanBindMoneyCode;
    }

    public void setIsCanBindMoneyCode(int isCanBindMoneyCode) {
        this.isCanBindMoneyCode = isCanBindMoneyCode;
    }

    public int getIsCanBindPosPackageTemplate() {
        return isCanBindPosPackageTemplate;
    }

    public void setIsCanBindPosPackageTemplate(int isCanBindPosPackageTemplate) {
        this.isCanBindPosPackageTemplate = isCanBindPosPackageTemplate;
    }

    public Long getPosTerminalId() {
        return posTerminalId;
    }

    public void setPosTerminalId(Long posTerminalId) {
        this.posTerminalId = posTerminalId;
    }

    public String getPosTerminalName() {
        return posTerminalName;
    }

    public void setPosTerminalName(String posTerminalName) {
        this.posTerminalName = posTerminalName;
    }

    public Long getShopStoreId() {
        return shopStoreId;
    }

    public void setShopStoreId(Long shopStoreId) {
        this.shopStoreId = shopStoreId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getPosTerminalNo() {
        return posTerminalNo;
    }

    public void setPosTerminalNo(String posTerminalNo) {
        this.posTerminalNo = posTerminalNo;
    }

    public String getPosTerminalSerialNo() {
        return posTerminalSerialNo;
    }

    public void setPosTerminalSerialNo(String posTerminalSerialNo) {
        this.posTerminalSerialNo = posTerminalSerialNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }


    public PosTerminal() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.posTerminalId);
        dest.writeValue(this.shopStoreId);
        dest.writeString(this.storeName);
        dest.writeString(this.posTerminalNo);
        dest.writeString(this.posTerminalSerialNo);
        dest.writeString(this.posTerminalName);
        dest.writeString(this.productName);
        dest.writeString(this.productImage);
        dest.writeLong(this.posPackageTemplateId);
        dest.writeLong(this.bindMoneyCodeCount);
        dest.writeInt(this.isCanBindMoneyCode);
        dest.writeInt(this.isCanBindPosPackageTemplate);
    }

    protected PosTerminal(Parcel in) {
        this.posTerminalId = (Long) in.readValue(Long.class.getClassLoader());
        this.shopStoreId = (Long) in.readValue(Long.class.getClassLoader());
        this.storeName = in.readString();
        this.posTerminalNo = in.readString();
        this.posTerminalSerialNo = in.readString();
        this.posTerminalName = in.readString();
        this.productName = in.readString();
        this.productImage = in.readString();
        this.posPackageTemplateId = in.readLong();
        this.bindMoneyCodeCount = in.readLong();
        this.isCanBindMoneyCode = in.readInt();
        this.isCanBindPosPackageTemplate = in.readInt();
    }

    public static final Creator<PosTerminal> CREATOR = new Creator<PosTerminal>() {
        @Override
        public PosTerminal createFromParcel(Parcel source) {
            return new PosTerminal(source);
        }

        @Override
        public PosTerminal[] newArray(int size) {
            return new PosTerminal[size];
        }
    };
}
