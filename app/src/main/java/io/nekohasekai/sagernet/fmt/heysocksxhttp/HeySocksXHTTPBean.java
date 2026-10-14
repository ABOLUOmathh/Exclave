package io.nekohasekai.sagernet.fmt.heysocksxhttp;

import androidx.annotation.NonNull;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.ByteBufferOutput;
import io.nekohasekai.sagernet.fmt.AbstractBean;
import io.nekohasekai.sagernet.fmt.KryoConverters;

/** Exclave profile state for the proprietary HeySocks XHTTP/xstream protocol. */
public class HeySocksXHTTPBean extends AbstractBean {
    public String password;
    public String method;
    public String fakeTCP;
    public String fakeUDP;
    public String paddingLen;
    public boolean udp;
    public boolean uot;
    public int uotVersion = 0; // Native HeySocks: 0 -> current UOT v2.
    public boolean e;
    public int linkVersion = 1;

    @Override
    public void initializeDefaultValues() {
        super.initializeDefaultValues();
        if (password == null) password = "";
        if (method == null) method = "aes-128-ctr";
        if (fakeTCP == null) fakeTCP = "";
        if (fakeUDP == null) fakeUDP = "";
        if (paddingLen == null) paddingLen = "";
    }

    @Override
    public void serialize(ByteBufferOutput output) {
        output.writeInt(1); // Bean serialization format, independent of URI protocol v.
        super.serialize(output);
        output.writeString(serverAddress);
        output.writeInt(serverPort);
        output.writeString(password);
        output.writeString(method);
        output.writeString(fakeTCP);
        output.writeString(fakeUDP);
        output.writeString(paddingLen);
        output.writeBoolean(udp);
        output.writeBoolean(uot);
        output.writeInt(uotVersion);
        output.writeBoolean(e);
        output.writeInt(linkVersion);
    }

    @Override
    public void deserialize(ByteBufferInput input) {
        int formatVersion = input.readInt();
        if (formatVersion != 1) throw new IllegalArgumentException("Unsupported HeySocks XHTTP bean version: " + formatVersion);
        super.deserialize(input);
        serverAddress = input.readString();
        serverPort = input.readInt();
        password = input.readString();
        method = input.readString();
        fakeTCP = input.readString();
        fakeUDP = input.readString();
        paddingLen = input.readString();
        udp = input.readBoolean();
        uot = input.readBoolean();
        uotVersion = input.readInt();
        e = input.readBoolean();
        linkVersion = input.readInt();
    }

    public String protocolName() { return "黑丝 XHTTP"; }

    @NonNull
    @Override
    public HeySocksXHTTPBean clone() {
        return KryoConverters.deserialize(new HeySocksXHTTPBean(), KryoConverters.serialize(this));
    }
}
