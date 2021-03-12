<#assign base=request.contextPath />
<!DOCTYPE html>
<html>
<head>
    <base id="base" href="${base}">
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=9;IE=8;IE=7;IE=EDGE">
    <title></title>
    <style>
        *{
            margin:0; padding:0;
        }
        html,body{
            width: 100%;height: 100%;
        }

        body{
            background: url(../images/banner.jpg) no-repeat;
            background-size: 100% 100%;
            position: relative;

        }

    </style>
</head>
<body>
host测试
${request.contextPath}
</body>

