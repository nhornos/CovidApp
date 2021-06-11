package Clases;

import android.util.Log;

public class cEstructuras {

    public static class cUsuario{
        private int dni;
        private String email;
        private String token;
        private String tokenRefresh;

        public int getDni() {
            return dni;
        }

        public void setDni(int dni) {
            this.dni = dni;
        }

        public String getEmail(){
            return this.email;
        }

        public void setEmail(String email){
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenRefresh() {
            return tokenRefresh;
        }

        public void setTokenRefresh(String tokenRefresh) {
            this.tokenRefresh = tokenRefresh;
        }

        public void setDataUsuario(String email, String token, String tokenRefresh){
            setEmail(email);
            setToken(token);
            setTokenRefresh(tokenRefresh);
        }

        public void printDataUsuario(){
            Log.i("Data user:", "DNI:" + dni + "\nemail:" + email + "\ntoken:" + token);
        }
    }
}
