/*
 * Copyright (c) 2022 by Submaryne(how to username#1337)
 * Permission to modify and use this software, is only granted to any person with Submaryne(how to username#1337)'s allowance.
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.anchorclient.client.connection;

import com.anchorclient.client.connection.packet.Packet;
import com.anchorclient.client.connection.packet.PacketBuilder;
import com.anchorclient.client.connection.packet.impl.client.CPacketHandshake;
import net.minecraft.client.Minecraft;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class Connection extends WebSocketClient {

    public Connection(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake data) {
        CPacketHandshake handshake = new CPacketHandshake(Minecraft.getMinecraft().getSession().getProfile().getId() == null ? "no-uuid" : Minecraft.getMinecraft().getSession().getProfile().getId().toString(), Minecraft.getMinecraft().getSession().getUsername());
        sendPacket(handshake);
    }

    @Override
    public void onMessage(String message) {
        Packet packet = PacketBuilder.build(message);
        packet.process();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void sendPacket(Packet packet){
        this.send(PacketBuilder.encode(packet));
    }

}
