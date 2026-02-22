package com.example.taskmanagerapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.taskmanagerapp1.ui.theme.TaskManagerApp1Theme
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerApp1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

//holds the string and lets user toggle task completion status of a single task
data class Task(
    val name: String,
    val initialComplete: Boolean = false
) {
    var isComplete by mutableStateOf(initialComplete)
}

//includes all functions in one space and all components are padded per assignment
@Composable
fun MainScreen(modifier: Modifier) {
    //tracks adding & deleting to refresh the LazyColumn
    val taskList = remember { mutableStateListOf<Task>(Task("Your First Task", false)) }

    //padding and spacing placed for all components per requirement
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Task Manager",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TaskInputField(taskList)
        TaskList(taskList)
    }
}


@Composable
fun TaskInputField(taskList: SnapshotStateList<Task>) {
    //tracks what user is currently typing in TextField
    var text by remember {mutableStateOf("")}
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { newText -> text = newText},
            label = {Text("Enter Task")},
            modifier = Modifier.weight(1f)
        )

        //add new task to state list and reset input field
        Button(onClick = {
            taskList.add(Task(name = text, initialComplete = false))
            text = ""
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            )
        ) {
            Text(
                text = "Add Task",

            )
        }
    }
}

@Composable
fun TaskItem(task: Task, taskList: SnapshotStateList<Task>){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding (vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

    ){
        Row(verticalAlignment = Alignment.CenterVertically) {
            //updates task state if completed or not completed
            Checkbox(
                checked = task.isComplete,
                onCheckedChange = { task.isComplete = it }
            )
            Text(
                text = task.name,
                modifier = Modifier.padding(start = 8.dp),
                textDecoration = if (task.isComplete) {
                    TextDecoration.LineThrough
                } else {
                    null
                }
            )
        }

        IconButton(onClick = { taskList.remove(task) }) {
            Text("️🗑️")
        }
    }
}


@Composable
fun TaskList(taskList: SnapshotStateList<Task>) {
    //allows vertical scrolling for long lists
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(taskList) { task ->
            TaskItem(task, taskList) // Passing individual task to TaskItem
        }
    }
}



