package com.scout.myplayer.ui.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.scout.myplayer.R
import com.scout.myplayer.data.Song
import com.scout.myplayer.viewmodel.PlayerViewModel
import com.scout.myplayer.utils.StringUtils

@Composable
fun ShowPlaylistDialog(showDialog : Boolean, viewModel: PlayerViewModel,
                       onDismissRequest: () -> Unit, onSongSelected: (Int) -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest ,
            confirmButton = {},
            dismissButton = {},
            text = {
                PlaylistScreenContent(
                    onSongSelected = {
                            index -> onSongSelected(index)
                        onDismissRequest()
                    }, viewModel
                )
            }
        )
    }
}

@Composable
fun PlaylistScreenContent(onSongSelected: (Int) -> Unit, viewModel: PlayerViewModel) {
    val songs = viewModel.playlist
    Column(modifier = Modifier.fillMaxSize()) {
        TitleLabel()
        Spacer(modifier = Modifier.height(16.dp))
        SongList(songs, onSongSelected)
    }
}

@Composable
fun TitleLabel() {
    Text(
        text = stringResource(id = R.string.library), // Localized string can be used here
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SongList(songs : List<Song>, onSongSelected: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(items = songs) {song ->
            SongItem(index = song.id, songTitle = song.title, duration = song.duration,
                artist = song.artistName, onSongSelected)
        }
    }
}

@Composable
fun SongItem(index : Int, songTitle: String, duration:String, artist: String,
             onSongSelected: (Int) -> Unit) {
    // Implement your charger item composable here
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onSongSelected(index)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
                Text(
                    text = songTitle,
                    color = MaterialTheme.colorScheme.primary, // Assuming Color.theme is equivalent to Color.Theme
                    style = MaterialTheme.typography.titleMedium, // Adjust the font size and style as needed
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = StringUtils.formatMSToTime(duration),
                    color = MaterialTheme.colorScheme.secondary, // Assuming Color.theme is equivalent to Color.Theme
                    style = MaterialTheme.typography.bodySmall, // Adjust the font size and style as needed
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = artist,
                    color = MaterialTheme.colorScheme.tertiary, // Assuming Color.theme is equivalent to Color.Theme
                    style = MaterialTheme.typography.bodyMedium, // Adjust the font size and style as needed
                    modifier = Modifier.padding(bottom = 8.dp)
                )

            }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.secondary) // Assuming Color.secondTheme is equivalent to Color.SecondTheme
        )
    }
}

@Preview
@Composable
fun PlaylistScreenPreview() {
    PlaylistScreenContent({}, hiltViewModel())
}