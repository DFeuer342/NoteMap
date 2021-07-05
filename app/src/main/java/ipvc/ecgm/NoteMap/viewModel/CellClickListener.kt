package ipvc.ecgm.NoteMap.viewModel

import ipvc.ecgm.NoteMap.entities.Nota

interface CellClickListener {
    fun onCellClickListener (data: Nota)
}