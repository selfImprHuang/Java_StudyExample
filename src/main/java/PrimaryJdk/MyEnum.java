package PrimaryJdk;


public enum MyEnum {
    /**
     * 小仙女
     */
    Fairies(){
        @Override
        public void doSomething() {
            System.out.println("做饭");
        }

        @Override
        public void saySomething() {
            System.out.println("我是小仙女");
        }
    },
    /**
     * 小无赖
     */
    Rogue(){
        @Override
        public void doSomething() {
            System.out.println("洗碗");
        }

        @Override
        public void saySomething() {
            System.out.println("我是小无赖");
        }
    };

    MyEnum() {
    }

    public abstract  void doSomething();

    public abstract  void saySomething();
}
