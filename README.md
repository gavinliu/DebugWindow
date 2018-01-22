# DebugWindow

* Shake phone to show DebugWindow

## Usage

### Dependencies

```
implementation 'cn.gavinliu.android.lib:DebugWindow:1.0.0'
```

### Coding

+ #### With Kotlin

```
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // init library
        DebugWindow.getInstance(this)
                .bindMainActivity(MainActivity::class.java) // binding your app main activity
                .bindDebugLayout(R.layout.debug) // binding your debug ui
                .bindView(MyViewBinder()) // binding your debug ui item
    }

    class MyViewBinder : DebugWindow.ViewBinder {
        override fun bindView(view: View) {
            val close: Button = view.findViewById(R.id.close)
            close.setOnClickListener {
                DebugWindow.get().hide()
            }
        }
    }
}
```

+ #### With Java

```
DebugWindow.Companion.getInstance(context)
    .bindDebugLayout(R.layout.debug_window)
    .bindMainActivity(MainActivity.class)
    .bindView(new DebugWindow.ViewBinder() {
        @Override
        public void bindView(View view) {

        }
    });
```

# License

The Apache License Version 2.0, January 2004
