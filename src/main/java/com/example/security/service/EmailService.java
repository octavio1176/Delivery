package com.example.security.service;
import com.example.security.domain.entity.Order;
import com.example.security.domain.entity.OrderItem;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String code) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Confirme sua conta");

        String html = """
        <!DOCTYPE html>
        <html>
        <body style="font-family: Arial, sans-serif; background:#f4f4f4; padding:40px;">

            <div style="max-width:600px; margin:auto; background:white; padding:30px;
                        border-radius:10px; box-shadow:0 2px 8px rgba(0,0,0,.1);">

                <h2 style="color:#2c3e50;">
                    Sistema de Delivery
                </h2>

                <p>Olá!</p>

                <p>Obrigado por criar uma conta.</p>

                <p>Use o código abaixo para confirmar seu cadastro:</p>

                <div style="
                    text-align:center;
                    font-size:32px;
                    font-weight:bold;
                    letter-spacing:8px;
                    background:#f5f5f5;
                    padding:20px;
                    border-radius:8px;
                    margin:25px 0;">
                    %s
                </div>

                <p>Este código expira em alguns minutos.</p>

                <hr>

                <small style="color:gray;">
                    Se você não solicitou este cadastro, ignore este e-mail.
                </small>

            </div>

        </body>
        </html>
        """.formatted(code);

        helper.setText(html, true);

        mailSender.send(message);
    }
    public void sendCode(String to, String code){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirme sua conta ");
        message.setText(" Codigo de recuperacao : " + code+
                "  Este código expira em alguns minutos.");

        mailSender.send(message);
    }
    public void sendOrderConfirmationEmail(String to, Order order) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("Pedido #" + order.getId() + " confirmado!");

        String itemsHtml = buildItemsHtml(order.getOrderItems());

        String html = """
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>

<body style="
    margin:0;
    padding:0;
    background:#f5f7fa;
    font-family:Arial, Helvetica, sans-serif;
">

<table width="100%%" cellpadding="0" cellspacing="0">
<tr>
<td align="center" style="padding:40px 0;">

<div style="
    width:600px;
    max-width:90%%;
    background:white;
    border-radius:16px;
    overflow:hidden;
    box-shadow:0 5px 20px rgba(0,0,0,0.08);
">


<!-- HEADER -->

<div style="
    background:#ff6b35;
    padding:30px;
    text-align:center;
    color:white;
">

<h1 style="
    margin:0;
    font-size:28px;
">
🍔 Delivery Express
</h1>

<p style="
    margin-top:10px;
    font-size:15px;
">
Seu pedido foi confirmado!
</p>

</div>


<!-- CONTENT -->

<div style="padding:35px;">


<h2 style="
color:#333;
">
Olá!
</h2>


<p style="
font-size:16px;
color:#555;
line-height:1.5;
">

Recebemos seu pedido 
<strong>#%d</strong>.

Nossa equipe já começou a preparar tudo para você.

</p>



<div style="
background:#f8f9fa;
padding:20px;
border-radius:12px;
margin:25px 0;
">


<p style="
margin:0;
color:#777;
font-size:14px;
">
STATUS DO PEDIDO
</p>


<h3 style="
margin:8px 0 0;
color:#ff6b35;
">
%s
</h3>


</div>





<table width="100%%" 
style="
border-collapse:collapse;
margin-top:25px;
">


<thead>

<tr style="
background:#333;
color:white;
">

<th style="padding:12px;text-align:left;">
Produto
</th>

<th style="padding:12px;">
Qtd
</th>

<th style="padding:12px;text-align:right;">
Preço
</th>

<th style="padding:12px;text-align:right;">
Subtotal
</th>


</tr>

</thead>


<tbody>

%s

</tbody>


</table>




<div style="
margin-top:30px;
padding:20px;
background:#fff3ed;
border-radius:12px;
text-align:right;
">


<span style="
font-size:16px;
color:#555;
">
Total do pedido
</span>


<br>


<strong style="
font-size:28px;
color:#ff6b35;
">
%s MT
</strong>


</div>




<div style="
text-align:center;
margin-top:35px;
">


<a href="#"
style="
display:inline-block;
background:#ff6b35;
color:white;
padding:14px 30px;
border-radius:30px;
text-decoration:none;
font-weight:bold;
">

Acompanhar Pedido

</a>


</div>


</div>





<!-- FOOTER -->


<div style="
background:#fafafa;
padding:20px;
text-align:center;
font-size:13px;
color:#888;
">


<p>
Obrigado por escolher o Delivery Express.
</p>


<p>
Caso não reconheça este pedido,
contacte nosso suporte.
</p>


</div>



</div>


</td>
</tr>
</table>


</body>
</html>

""".formatted(
                order.getId(),
                order.getOrderStatus(),
                itemsHtml,
                order.getTotal().setScale(2, RoundingMode.HALF_UP)
        );


        helper.setText(html, true);
        mailSender.send(message);
    }


    private String buildItemsHtml(List<OrderItem> items) {
        StringBuilder rows = new StringBuilder();

        for (OrderItem item : items) {
            BigDecimal subtotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            rows.append("""
            <tr style="border-bottom:1px solid #eee;">
                <td style="padding:10px;">%s</td>
                <td style="padding:10px; text-align:center;">%d</td>
                <td style="padding:10px; text-align:right;">%s MT</td>
                <td style="padding:10px; text-align:right;">%s MT</td>
            </tr>
            """.formatted(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getPrice().setScale(2, RoundingMode.HALF_UP),
                    subtotal.setScale(2, RoundingMode.HALF_UP)
            ));
        }

        return rows.toString();
    }

}