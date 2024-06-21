package com.scout.myplayer.ui.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.scout.myplayer.R
import com.scout.myplayer.data.ControlButtons
import com.scout.myplayer.ui.playlist.ShowPlaylistDialog
import com.scout.myplayer.ui.theme.MyPlayerTheme
import com.scout.myplayer.viewmodel.PlayerViewModel

@Composable
fun PlayerScreen (viewModel: PlayerViewModel) {

    LaunchedEffect(Unit) {
        viewModel.preparePlayer()
    }

    PlayerScreenContent(viewModel)
}

@Composable
fun PlayerScreenContent(viewModel: PlayerViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val isPlaying by viewModel.isPlaying.collectAsState()
    val index by viewModel.currentPlayingIndex.collectAsState()
    val song = viewModel.playlist[index]
    Surface {
        ShowPlaylistDialog(showDialog = showDialog, viewModel, onDismissRequest = { showDialog = false }, viewModel::playSongByIndex)

        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly){
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = song.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = song.artistName,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween){
                IconButton(onClick = { viewModel.updatePlaylist(ControlButtons.Previous) }) {
                    Icon(painter = painterResource(id = R.drawable.skip_previous), contentDescription = "Previous")
                }
                IconButton(onClick = {viewModel.updatePlaylist(ControlButtons.Play)}) {
                    Icon(painter = painterResource(id = if (isPlaying) R.drawable.pause else R.drawable.play_arrow), contentDescription = "play")
                }
                IconButton(onClick = { viewModel.updatePlaylist(ControlButtons.Next) }) {
                    Icon(painter = painterResource(id = R.drawable.skip_next), contentDescription = "Previous")
                }
                IconButton(onClick = { showDialog = true }) {
                    Icon(painter = painterResource(id = R.drawable.queue_music), contentDescription = "Playlist")
                }
            }
        }
    }
}

@Preview
@Composable
fun PlayerScreenPreview() {
    MyPlayerTheme {
        PlayerScreenContent(hiltViewModel())
    }
}