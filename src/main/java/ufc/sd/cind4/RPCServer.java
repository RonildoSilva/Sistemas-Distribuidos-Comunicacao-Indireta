package ufc.sd.cind4;

import java.io.IOException;
import java.util.Map;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import ufc.sd.cind.config.Config;
import ufc.sd.cind.util.ManipuladorArquivos;
  
public class RPCServer {
  
  private static final String RPC_QUEUE_NAME = "rpc_queue";
  
//  private static int fib(int n) {
//    if (n ==0) return 0;
//    if (n == 1) return 1;
//    return fib(n-1) + fib(n-2);
//  }
  
  private static String fib(String n) throws IOException {
	ManipuladorArquivos mq = new ManipuladorArquivos();
	Map<String, String> dic = mq.carregar();
	return dic.get("men");
  }
     
  public static void main(String[] argv) {
    Connection connection = null;
    Channel channel = null;
    try {
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost(Config.IP);
  
      connection = factory.newConnection();
      channel = connection.createChannel();
      
      channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
  
      channel.basicQos(1);
  
      QueueingConsumer consumer = new QueueingConsumer(channel);
      channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
  
      System.out.println(" [x] Awaiting RPC requests");
  
      while (true) {
        String response = null;
        
        QueueingConsumer.Delivery delivery = consumer.nextDelivery();
        
        BasicProperties props = delivery.getProperties();
        BasicProperties replyProps = new BasicProperties
                                         .Builder()
                                         .correlationId(props.getCorrelationId())
                                         .build();
        
        try {
          String message = new String(delivery.getBody(),"UTF-8");
//          int n = Integer.parseInt(message);
          
          System.out.println(" [.] Traducao [" + message + "]");
//          response = "" + fib(n);
          response = "" + fib(message);
        }
        catch (Exception e){
          System.out.println(" [.] " + e.toString());
          response = "";
        }
        finally {  
          channel.basicPublish( "", props.getReplyTo(), replyProps, response.getBytes("UTF-8"));
  
          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
      }
    }
    catch  (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (connection != null) {
        try {
          connection.close();
        }
        catch (Exception ignore) {}
      }
    }      		      
  }
}
