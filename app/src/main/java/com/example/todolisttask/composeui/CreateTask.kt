import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolisttask.models.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTask(navController: NavController, onSaveClick: (Task) -> Unit) {
    var taskName by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = taskName,
            onValueChange = { taskName = it },
            label = { Text("Название задания") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text("Описание задания") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .heightIn(max = 200.dp)
                .padding(16.dp),
            readOnly = false
        )

        Spacer(modifier = Modifier.height(16.dp))

//        Button(
//            onClick = {
//                if (taskName.isNotEmpty()) {
//                    val newTask = Task(0, taskName, taskDescription)
//                    onSaveClick(newTask)
//                    taskName = ""
//
//                    navController.popBackStack()
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Сохранить")
//        }
    }
}
