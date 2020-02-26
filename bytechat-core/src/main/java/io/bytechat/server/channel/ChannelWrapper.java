package io.bytechat.server.channel;

import com.sun.tracing.dtrace.ArgsAttributes;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : denglinhai
 * @date : 22:11 2020/2/26
 * channel封装类
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ChannelWrapper {

    private Channel channel;

    private ChannelType channelType;

    @Override
    public String toString(){
        return "channel[+"+channel.id()+"+],"+"channelType["+channelType+"]";
    }
}
