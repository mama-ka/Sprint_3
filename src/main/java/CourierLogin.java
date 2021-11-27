public class CourierLogin {

        public final String login;
        public final String password;


        public CourierLogin (String login, String password) {
            this.login = login;
            this.password = password;

        }
        static public CourierLogin from (CourierRegister courier){
            return new CourierLogin (courier.login, courier.password);
}
}