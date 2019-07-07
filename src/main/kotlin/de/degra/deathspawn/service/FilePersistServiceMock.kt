package de.degra.deathspawn.service

import de.degra.deathspawn.models.DeathspawnEntry

class FilePersistServiceMock : IFilePersistService {

    var list: MutableList<DeathspawnEntry> = mutableListOf()

    override fun saveToFile(list: MutableList<DeathspawnEntry>) {
        this.list = list;
    }

    override fun readFromFile(): MutableList<DeathspawnEntry> {
        return list;
    }
}