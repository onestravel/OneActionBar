package cn.onestravel.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import cn.onestravel.one.actionbar.OneActionBar
import com.example.demo.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        oneActionBar.type = OneActionBar.Type.TYPE_BACK_SEARCH
        oneActionBar.setOnBackClickListener(object : OneActionBar.OnBackClickListener {
            override fun onBackClick() {
                Toast.makeText(this@MainActivity, "返回1", Toast.LENGTH_SHORT).show()

            }

        })
        oneActionBar.setOnBackClickListener {
            Toast.makeText(this, "返回", Toast.LENGTH_SHORT).show()
        }

    }
}
