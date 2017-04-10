package br.com.app.loginapplication.activitys.control;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by marcos on 09/04/2017.
 */

public class ConectaHttp {

    public static String getDados(String urlString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resposta = null;
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null){
                buffer.append(line);
            }
            resposta = buffer.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            try {
                reader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return resposta;
    }

    public static String putDados(String urlUsuario, String parametrosUsuario){
        URL url;
        HttpURLConnection connection=null;

        try{
            //pega a url no parametro
            url=new URL(urlUsuario);
            //abre a conexão
            connection=(HttpURLConnection) url.openConnection();
            //configura o metodo de envio como PUT
            connection.setRequestMethod("PUT");
            //configura as propriedades do arquivo de envio de acordo com essa lista : https://api.cakephp.org/3.0/class-Cake.Network.Response.html#$_mimeTypes
            connection.setRequestProperty("Content-Type","application/json");
            //configura o tamanho de bytes que sera enviado de acordo com o tamanho da string parametrosUsuario
            connection.setRequestProperty("Content-Lenght",""+Integer.toString(parametrosUsuario.getBytes().length));
            //configura a linguagem do texto
            connection.setRequestProperty("Content-Language","pt-BR");
            //para que nenhum dado seja armazenado na máquina é necessario apagar o cache
            connection.setUseCaches(false);
            //habilitar entrada e saída de dados da aplicação
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //armazena os dados de saída
            DataOutputStream dataOutputStream=new DataOutputStream(connection.getOutputStream());
            //fazer o envio da solicitação de dados
            dataOutputStream.writeBytes(parametrosUsuario);
            dataOutputStream.flush();
            dataOutputStream.close();
            //obter a informação
            InputStream inputStream=connection.getInputStream();
            //colocar os dados no buffer de leitura
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            //cria uma string pra enviar esses dados
            String linha;
            //cria um StringBuffer pra captar a resposta
            StringBuffer resposta = new StringBuffer();
            //laço while para capturar os dados linha por linha
            //enquanto houver informação pode continuar lendo
            while((linha=bufferedReader.readLine())!=null){
                //junta uma string apos a outra com append
                resposta.append(linha);
                resposta.append("\r");
            }
            //fecha a entrada de dados
            bufferedReader.close();
            //retorna os dados da requisição
            return resposta.toString();

        }catch (Exception erro){
            return null;

        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }
    }

}
