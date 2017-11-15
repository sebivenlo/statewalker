enum MyStates extends State {
    S1{
	...
    },
    S2{
	...
    },
    S33{
	
        @Override
        public void enter(Context ctx) {
            ctx.getDevice().heater(true); //<1>
        }

        @Override
        public void exit(Context ctx) {
            ctx.getDevice().heater(false); //<2>
        }

        @Override
        public void e10(Context ctx) {
            ctx.changeFromToState("e10", this, S31);//<3>
        }
    }
    NULL{// handlers for all events with empty bodies
	void e1(Context ctx){}
	...

	    };
}

