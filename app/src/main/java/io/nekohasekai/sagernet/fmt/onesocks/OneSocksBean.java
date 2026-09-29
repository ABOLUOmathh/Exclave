package io.nekohasekai.sagernet.fmt.onesocks;

import androidx.annotation.NonNull;

import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;

import org.jetbrains.annotations.NotNull;

import io.nekohasekai.sagernet.fmt.KryoConverters;
import io.nekohasekai.sagernet.fmt.shadowsocks.ShadowsocksBean;


public class OneSocksBean extends ShadowsocksBean {

    public String paddingLen;


    @Override
    public void initializeDefaultValues() {

        super.initializeDefaultValues();

        if (method == null)
            method = "aes-128-ctr";

        if (paddingLen == null)
            paddingLen = "";

    }


    @Override
    public void serialize(ByteBufferOutput output) {

        output.writeInt(1);

        super.serialize(output);

        output.writeString(paddingLen);

    }


    @Override
    public void deserialize(ByteBufferInput input) {

        int version = input.readInt();

        super.deserialize(input);

        paddingLen = input.readString();

    }


    @Override
    public String protocolName() {
        return "OneSocks";
    }


    @NotNull
    @Override
    public OneSocksBean clone() {

        return KryoConverters.deserialize(
                new OneSocksBean(),
                KryoConverters.serialize(this)
        );

    }


    public static final Creator<OneSocksBean> CREATOR =
            new Creator<>() {

        @NonNull
        @Override
        public OneSocksBean newInstance() {
            return new OneSocksBean();
        }


        @Override
        public OneSocksBean[] newArray(int size) {
            return new OneSocksBean[size];
        }
    };

}
