package io.nekohasekai.sagernet.fmt.onesocks;

import androidx.annotation.NonNull;

import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;

import org.jetbrains.annotations.NotNull;

import io.nekohasekai.sagernet.fmt.AbstractBean;
import io.nekohasekai.sagernet.fmt.KryoConverters;


public class OneSocksBean extends AbstractBean {

    public String password;

    public String method;

    public String paddingLen;

    public boolean udp;

    public boolean uot;

    public int version = 1;



    @Override
    public void initializeDefaultValues() {

        super.initializeDefaultValues();


        if(method == null)
            method = "aes-128-ctr";


        if(password == null)
            password="";


        if(paddingLen == null)
            paddingLen="";

    }



    @Override
    public void serialize(ByteBufferOutput output){

        output.writeInt(1);

        super.serialize(output);


        output.writeString(serverAddress);
        output.writeInt(serverPort);


        output.writeString(method);
        output.writeString(password);
        output.writeString(paddingLen);

        output.writeBoolean(udp);
        output.writeBoolean(uot);

    }



    @Override
    public void deserialize(ByteBufferInput input){

        int version=input.readInt();

        super.deserialize(input);


        serverAddress=input.readString();

        serverPort=input.readInt();


        method=input.readString();

        password=input.readString();

        paddingLen=input.readString();

        udp=input.readBoolean();

        uot=input.readBoolean();

    }



    public String protocolName(){
        return "OneSocks";
    }



    @NotNull
    public OneSocksBean clone(){

        return KryoConverters.deserialize(
                new OneSocksBean(),
                KryoConverters.serialize(this)
        );

    }



}