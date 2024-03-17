package io.github.antwhale.codelab

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.antwhale.codelab.ui.theme.ComposeStateCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStateCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WellnessScreen()
                }
            }
        }
    }
}



@Composable
fun WellnessScreen(modifier: Modifier = Modifier, wellnessViewModel: WellnessViewModel = viewModel()) {
//    WaterCounter(modifier)
    Column(modifier = modifier) {

        StatefulCounter(modifier)

//        val list = remember { getWellnessTasks().toMutableStateList() }
        WellnessTasksList(list = wellnessViewModel.tasks, onCheckedTask = {task, checked -> wellnessViewModel.changeTaskChecked(task, checked)}, onCloseTask = {task -> wellnessViewModel.remove(task)})
    }
}

//@Composable
//fun WaterCounter(modifier: Modifier = Modifier) {
//    Column(modifier = modifier.padding(16.dp)) {
//         var count by remember { mutableStateOf(0) }
//
//        if(count > 0) {
//            var showTask by remember { mutableStateOf(true) }
//            if(showTask) {
//                WellnessTaskItem(
//                    onClose = { showTask = false },
//                    taskName = "Have you taken your 15 minute walk today?"
//                )
//            }
//            Text(text = "You've had $count glasses.",)
//        }
//
//        Row(Modifier.padding(top = 8.dp)) {
//            Button(onClick = { count++ }, enabled = count < 10) {
//                Text("Add one")
//            }
//            Button(
//                onClick = { count = 0 },
//                Modifier.padding(start = 8.dp)) {
//                Text("Clear water count")
//            }
//        }
//
//    }
//}

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        var count by rememberSaveable { mutableStateOf(0) }
        if (count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        if(count > 0) {
            Text("You've had $count glasses.")
        }
        Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}

@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }
    StatelessCounter(count = count, onIncrement = { count++ }, modifier)
}

//@Composable
//fun WellnessTaskItem(
//    taskName: String,
//    onClose: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Row(
//        modifier = modifier, verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(
//            modifier = Modifier
//                .weight(1f)
//                .padding(start = 16.dp),
//            text = taskName
//        )
//        IconButton(onClick = onClose) {
//            Icon(Icons.Filled.Close, contentDescription = "Close")
//        }
//    }
//}

@Composable
fun WellnessTaskItem(taskName: String, modifier: Modifier = Modifier, onCheckedChange: (Boolean) -> Unit, onClose: () -> Unit) {
    var checkedState by rememberSaveable { mutableStateOf(false) }

    WellnessTaskItem(
        taskName = taskName,
        checked = checkedState,
        onCheckedChange =  onCheckedChange,
        onClose = onClose,
        modifier = modifier
    )
}

@Composable
fun WellnessTaskItem(
    taskName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = taskName
        )
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        IconButton(onClick = onClose) {
            Icon(Icons.Filled.Close, contentDescription = "Close")
        }
    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeStateCodelabTheme {
        Greeting("Android")
    }
}

class WellnessTask(val id: Int, val label: String, initialChecked: Boolean = false) {
    var checked by mutableStateOf(initialChecked)
}


