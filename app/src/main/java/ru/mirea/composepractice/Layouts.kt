package ru.mirea.composepractice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Hello(name: String?) {
    Text(name?.let { "Привет, $it!" } ?: "Имя не задано")
}

@Preview(name = "portrait", showSystemUi = true, device = "spec:width=360dp,height=640dp,orientation=portrait")
@Preview(name = "landscape", showSystemUi = true, device = "spec:width=360dp,height=640dp,orientation=landscape")
@Preview(name = "round", showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFF00,
    device = "spec:width=200dp,height=200dp,isRound=true")
@Composable
fun HelloPreview() {
    val background = if (LocalConfiguration.current.isScreenRound) Color.Yellow else Color.White
    Box(Modifier.fillMaxSize().background(background), contentAlignment = Alignment.Center) {
        Hello("Даниил")
    }
}



@Preview(showBackground = true)
@Composable
fun TextStyles() {
    val description = stringResource(R.string.compose_description)
    Column(
        Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(description, Modifier.fillMaxWidth(), color = Color.Green,
            fontSize = 16.sp, fontStyle = FontStyle.Italic, textAlign = TextAlign.Center)
        Text(description, Modifier.fillMaxWidth(), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(
            text = buildAnnotatedString {
                withStyle(ParagraphStyle(textIndent = TextIndent(firstLine = 48.sp))) {
                    append(description)
                }
            },
            modifier = Modifier.fillMaxWidth().background(Color.Green),
            color = Color.Black, fontSize = 24.sp, textDecoration = TextDecoration.Underline
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StyledButton() {
    Button(
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color.Gray),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black)
    ) { Text("Жмяк") }
}

@Preview(showBackground = true)
@Composable
fun InitialsBanner() {
    Box(
        Modifier.fillMaxWidth().height(100.dp).background(Color.LightGray).padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.size(68.dp).background(Color(0xFFFF5722), CircleShape),
            contentAlignment = Alignment.Center) {
            Text("ШДР", color = Color.Black)
        }
    }
}

@Preview
@Composable
fun CornerCircle() {
    Box(Modifier.size(240.dp, 120.dp).background(Color.Black)) {
        Image(painterResource(R.drawable.circle), "Оранжевый круг",
            Modifier.align(Alignment.TopEnd).size(48.dp))
    }
}

@Preview
@Composable
fun StretchedCircle() {
    Box(Modifier.size(240.dp, 120.dp).background(Color.Blue), contentAlignment = Alignment.Center) {
        Image(
            painterResource(R.drawable.circle), "Фиолетовый круг", Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds, colorFilter = ColorFilter.tint(Color(0xFF9C27B0))
        )
    }
}

private data class Contact(
    val name: String, val patronymic: String, val surname: String, val phone: String, val address: String
)

private val contacts = listOf(
    Contact("Евгений", "Андреевич", "Соколов", "+7 900 000-00-01", "Москва, ул. Лесная, д. 12"),
    Contact("Валентина", "Сергеевна", "Орлова", "+7 900 000-00-02", "Москва, ул. Полевая, д. 4"),
    Contact("Екатерина", "Григорьевна", "Крылова", "+7 900 000-00-03", "Москва, ул. Садовая, д. 10")
)

@Composable
private fun ContactDetails(contact: Contact, modifier: Modifier = Modifier, centered: Boolean = false) {
    Column(modifier, horizontalAlignment = if (centered) Alignment.CenterHorizontally else Alignment.Start) {
        listOf(
            "Имя: ${contact.name}", "Отчество: ${contact.patronymic}", "Фамилия: ${contact.surname}",
            "Мобильный телефон: ${contact.phone}", "Адрес: ${contact.address}"
        ).forEach {
            Text(it, style = MaterialTheme.typography.bodySmall,
                textAlign = if (centered) TextAlign.Center else TextAlign.Start)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SingleContactColumn() {
    ContactDetails(contacts.first(), Modifier.fillMaxWidth().padding(4.dp))
}

@Preview(showBackground = true)
@Composable
fun ContactListColumn() {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        contacts.forEach {
            ContactDetails(it, Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp), centered = true)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactRow() {
    Row(Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        ContactDetails(contacts.first(), Modifier.weight(1f), centered = true)
        Image(painterResource(R.drawable.circle), "Иллюстрация контакта", Modifier.size(36.dp))
    }
}

@Composable
fun ContactLayouts() {
    Column(Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Один контакт в Column")
        SingleContactColumn()
        Text("Список контактов в Column")
        ContactListColumn()
        Text("Контакт с изображением в Row")
        ContactRow()
    }
}
