<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Teşekkürler </title>
</head>

<body
        style="font-family: Arial, sans-serif; text-align: center; margin: 50px"
>
<h1 style="color: #333">Bize katıldığınız için teşekkürler ${Username}</h1>
<hr style="border: 1px solid #ccc; width: 50%; margin: 20px auto" />
<p style="color: #666">
    Lütfen ${Email} 'li hesabınızı aktive etmek için butona tıklayın.
</p>

<a href="http://localhost:7075/auth/changeStatus/${Id}" target= "_blank">
    <button style="padding: 10px 20px; font-size: 16px; background-color: #4caf50; color: white; border: none; border-radius: 5px; cursor: pointer;">
        Hesabı Aktive Et
    </button>
</a>
</body>
</html>