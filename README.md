# Deathspawn

Adds flag "dspawn" to WorldGuard.  
Respawn locations can be created with:  
`/region flag regionname dspawn here`  with your current position  
`/region flag regionname dspawn 300 100 200` with x y z coordinates

### MySQL
If you don't want a player's respawn point to be overwritten by the respawn location permanently, enable MySQL. 
If MySQL is enabled Deathspawn will restore a player's own respawn point  when the player dies outside a region with a set respawn point.