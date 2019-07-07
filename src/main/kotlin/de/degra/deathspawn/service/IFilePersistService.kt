package de.degra.deathspawn.service

import de.degra.deathspawn.models.DeathspawnEntry

interface IFilePersistService {

    fun readFromFile() : MutableList<DeathspawnEntry>

    fun saveToFile(list: MutableList<DeathspawnEntry>)
}