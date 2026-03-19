package com.lafabricadesoftware.rfidlaundry.data.data_source.remote

import com.lafabricadesoftware.rfidlaundry.data.data_source.HikariCPDataSource
import com.lafabricadesoftware.rfidlaundry.domain.model.*
import com.lafabricadesoftware.rfidlaundry.domain.model.ui.LecturaPrendaClienteSubCliente
import java.sql.SQLException
import java.sql.Types
import javax.inject.Inject

const val QUERY_TIMEOUT_5SEC = 5
const val QUERY_TIMEOUT_10SEC = 10
const val QUERY_TIMEOUT_30SEC = 30

class RemoteDataCollector @Inject constructor() {

    private val _getAllFromMasterClientes = "SELECT id, Nombre, FechaAlta, FechaBaja, Borrado, Vestuario " +
            "FROM MasterClientes "
    private val _getAllFromMasterSubClientes = "SELECT id, id_cliente, Nombre, Apellido1, Puesto, Borrado, Apellido2, FAlta, FBaja " +
            "FROM MasterSubClientes "
    private val _getAllFromMasterPrendas = "SELECT Id, id_ModeloPrenda, id_Cliente, id_subCliente, codigoTAG, descrip, FAlta, FBaja, borrado, " +
            "DateLastMov, LastIdMov, IdMaqLastMov FROM MasterPrendas "
    private val _getOneFromMasterPrendaByTag = "SELECT Id, id_ModeloPrenda, id_Cliente, id_subCliente, codigoTAG, descrip, FAlta, FBaja, borrado, DateLastMov, LastIdMov, LastMov " +
            "FROM MasterPrendas WHERE codigoTAG = '?' "
    private val _getOneFromMasterPrendaClienteSubClienteByTag = "SELECT TOP 1 mp.Id, mp.descrip AS NombrePrenda, mp.Talla AS Talla, mp.codigoTAG AS Tag, mp.borrado AS Borrado, mp.id_Cliente AS IdCliente, mp.id_SubCliente AS IdSubCliente, mc.Nombre AS NombreCliente, " +
            "msc.Nombre AS NombreSubCliente, msc.Apellido1 As Apellido1, msc.Apellido2 As Apellido2, mc.Borrado AS ClienteBorrado, msc.Borrado AS SubClienteBorrado, mp.id_ModeloPrenda AS IdModeloPrenda, mmp.Descripcion AS NombreModeloPrenda, mvp.Id AS IdLastMov, mvp.id_TipoAntena AS LastMovAntena, mvp.Fecha AS LastMovFecha, ISNULL(mp.LastMov, 0) LastMov, msc.Vestuario " +
            "FROM MasterPrendas mp INNER JOIN MasterClientes mc ON mp.id_Cliente = mc.id INNER JOIN MasterSubClientes msc ON mp.id_SubCliente = msc.id INNER JOIN MasterModeloPrenda mmp ON mp.id_ModeloPrenda = mmp.id LEFT JOIN MovPren mvp ON mp.Id = mvp.Id_Prenda " +
            "WHERE mp.codigoTAG = ? ORDER BY mp.FAlta DESC, mvp.Fecha DESC "
    private val _getAllFromMasterTipoAntena = "SELECT Id, descripcion, MostrarEnPDA, Mostrar, NoBorrar, EsBaja, PedirUbicacion " +
            "FROM MasterTipoAntena "
    private val _getAllFromMovMostrarAntenasPuesto = "SELECT idPuesto, idTipoAntena " +
            "FROM MovMostrarAntenasPuesto "
    private val _getLastMovPrenByIdPrenda = "SELECT TOP(1) Id, id_mov, Id_Prenda, Fecha, id_Puesto, id_TipoAntena, idCli, idSubCli, idModeloPrenda " +
            "FROM MovPren WHERE Id_Prenda = ? ORDER BY Fecha DESC "
    private val _setMovPren = "INSERT INTO MovPren (id_mov, Id_Prenda, Fecha, Hora, id_TipoAntena, idCli, idSubCli, idModeloPrenda, talla, id_Puesto) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) SELECT SCOPE_IDENTITY()"
    private val _getAllFromTaquilla = "SELECT Id, Estado, Usuario, Centro, Descripcion, DateLastMov " +
            "FROM MasterTaquilla "
    private val _getOneTaquillaByDescripcion = "SELECT TOP(1) Id, Estado, Usuario, Centro, Descripcion, DateLastMov " +
            "FROM MasterTaquilla WHERE Descripcion = ? ORDER BY Id DESC "


    fun initConnection(config: Configuracion): Boolean {
        return try {
            HikariCPDataSource.init(config, false)
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun testConnection(): Boolean {
        return try {
            val connection = HikariCPDataSource.getConnection()
//            val preparedStatement = connection.prepareStatement("SELECT 1")
//            val resultSet = preparedStatement.executeQuery()
//            preparedStatement.close()
            connection.close()
            println("----- testConnection: OK!")
            true
        } catch (e: SQLException) {
            println("----- testConnection: ${e.message}")
            false
        }
    }
    fun testConnectionWithParameters(config: Configuracion): Boolean {
        return try {
            val connection = HikariCPDataSource.getConnection(config, true)
            connection.close()
            println("----- testConnection: OK!")
            return true
        } catch (e: Exception) {
            println("----- testConnection: ${e.message}")
            false
        }
    }

    fun getClientes(): List<MasterClientes> {

        var toReturn = mutableListOf<MasterClientes>()
        try {
            val connection = HikariCPDataSource.getConnection()
            val preparedStatement = connection.prepareStatement(_getAllFromMasterClientes)
            preparedStatement.queryTimeout = QUERY_TIMEOUT_10SEC
            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                val item = MasterClientes(
                    id = resultSet.getInt("id"),
                    Nombre = resultSet.getString("Nombre"),
                    FechaAlta = if (resultSet.getString("FechaAlta") != null) { resultSet.getString("FechaAlta") } else { "" },
                    FechaBaja = if (resultSet.getString("FechaBaja") != null) { resultSet.getString("FechaBaja") } else { "" },
                    Borrado = resultSet.getBoolean("Borrado")
                )
                toReturn.add(item)
            }
            preparedStatement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getClientes: ${e.message}")
            throw e
        }
        return toReturn

//        val toReturn = mutableListOf<MasterClientes>()
//
//        val host = "lfdsazuresql.public.ed62102bb6b9.database.windows.net"
//        val port = "3342"
//        val database = "Laundry_ShowRoom"
//        val username = "lfs"
//        val password = "1Avand3r-$"
//        val url = "jdbc:jtds:sqlserver://$host:$port/$database"
//
////        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
////        StrictMode.setThreadPolicy(policy)
//
//        val query = "SELECT id, Nombre, FechaAlta, FechaBaja, Borrado FROM MasterClientes"
//
//        try {
//            DriverManager.getConnection(url, username, password).use { conn ->
//                conn.createStatement().use { stmt ->
//                    stmt.executeQuery(query).use { rs ->
//                        while (rs.next()) {
//                            val cliente = MasterClientes(
//                                id = rs.getInt("id"),
//                                Nombre = rs.getString("Nombre"),
//                                FechaAlta = if (rs.getString("FechaAlta") != null) { rs.getString("FechaAlta") } else { "" },
//                                FechaBaja = if (rs.getString("FechaBaja") != null) { rs.getString("FechaBaja") } else { "" },
//                                Borrado = rs.getBoolean("Borrado")
//                            )
//                            toReturn.add(cliente)
//                        }
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getClientes: ${e.message}")
//            throw e
//        }
//
//        return toReturn
    }
    fun getSubClientes(idCliente: Int = 0): List<MasterSubClientes> {

        val toReturn = mutableListOf<MasterSubClientes>()

        var query = _getAllFromMasterSubClientes
        if (idCliente > 0) {
            query += "WHERE id_cliente = $idCliente"
        }

        try {
            val connection = HikariCPDataSource.getConnection()
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.queryTimeout = QUERY_TIMEOUT_10SEC
            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                val item = MasterSubClientes(
                    id = resultSet.getInt("id"),
                    id_cliente = resultSet.getInt("id_cliente"),
                    Nombre = resultSet.getString("Nombre"),
                    Apellido1 = if (resultSet.getString("Apellido1") != null) { resultSet.getString("Apellido1") } else { "" },
                    Puesto = if (resultSet.getString("Puesto") != null) { resultSet.getString("Puesto") } else { "" },
                    Borrado = resultSet.getBoolean("Borrado"),
                    Apellido2 = if (resultSet.getString("Apellido2") != null) { resultSet.getString("Apellido2") } else { "" },
                    FAlta = if (resultSet.getString("FAlta") != null) { resultSet.getString("FAlta") } else { "" },
                    FBaja = if (resultSet.getString("FBaja") != null) { resultSet.getString("FBaja") } else { "" },
                    Vestuario = if (resultSet.getString("Vestuario") != null) { resultSet.getString("Vestuario") } else { "" }
                )
                toReturn.add(item)
            }
            preparedStatement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getSubClientes: ${e.message}")
            throw e
        }

        return toReturn

//        val host = "lfdsazuresql.public.ed62102bb6b9.database.windows.net"
//        val port = "3342"
//        val database = "Laundry_ShowRoom"
//        val username = "lfs"
//        val password = "1Avand3r-$"
//        val url = "jdbc:jtds:sqlserver://$host:$port/$database"
//
////        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
////        StrictMode.setThreadPolicy(policy)
//
//        var query = "SELECT id, id_cliente, Nombre, Apellido1, Puesto, Borrado, Apellido2, FAlta, FBaja FROM MasterSubClientes "
//        if (idCliente > 0) {
//            query += "WHERE id_cliente = $idCliente"
//        }
//
//        try {
//            DriverManager.getConnection(url, username, password).use { conn ->
//                conn.createStatement().use { stmt ->
//                    stmt.executeQuery(query).use { rs ->
//                        while (rs.next()) {
//                            val subCliente = MasterSubClientes(
//                                id = rs.getInt("id"),
//                                id_cliente = rs.getInt("id_cliente"),
//                                Nombre = rs.getString("Nombre"),
//                                Apellido1 = if (rs.getString("Apellido1") != null) { rs.getString("Apellido1") } else { "" },
//                                Puesto = if (rs.getString("Puesto") != null) { rs.getString("Puesto") } else { "" },
//                                Borrado = rs.getBoolean("Borrado"),
//                                Apellido2 = if (rs.getString("Apellido2") != null) { rs.getString("Apellido2") } else { "" },
//                                FAlta = if (rs.getString("FAlta") != null) { rs.getString("FAlta") } else { "" },
//                                FBaja = if (rs.getString("FBaja") != null) { rs.getString("FBaja") } else { "" }
//                            )
//                            toReturn.add(subCliente)
//                        }
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getSubClientes: ${e.message}")
//            throw e
//        }
//
//        return toReturn
    }
    fun getPrendas(idSubCliente: Int = 0): List<MasterPrendas> {

        val toReturn = mutableListOf<MasterPrendas>()

        var query = _getAllFromMasterPrendas
        if (idSubCliente > 0) {
            query += "WHERE id_subCliente = $idSubCliente"
        }

        try {
            val connection = HikariCPDataSource.getConnection()
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.queryTimeout = QUERY_TIMEOUT_10SEC
            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                val item = MasterPrendas(
                    Id = resultSet.getInt("Id"),
                    id_ModeloPrenda = resultSet.getInt("id_ModeloPrenda"),
                    id_Cliente = resultSet.getInt("id_Cliente"),
                    id_subCliente = resultSet.getInt("id_subCliente"),
                    codigoTAG = if (resultSet.getString("codigoTAG") != null) { resultSet.getString("codigoTAG") } else { "" },
                    descrip = if (resultSet.getString("descrip") != null) { resultSet.getString("descrip") } else { "" },
                    FAlta = if (resultSet.getString("FAlta") != null) { resultSet.getString("FAlta") } else { "" },
                    FBaja = if (resultSet.getString("FBaja") != null) { resultSet.getString("FBaja") } else { "" },
                    borrado = resultSet.getBoolean("borrado")
                )
                toReturn.add(item)
            }
            preparedStatement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getPrendas: ${e.message}")
            throw e
        }

        return toReturn

//        val host = "lfdsazuresql.public.ed62102bb6b9.database.windows.net"
//        val port = "3342"
//        val database = "Laundry_ShowRoom"
//        val username = "lfs"
//        val password = "1Avand3r-$"
//        val url = "jdbc:jtds:sqlserver://$host:$port/$database"
//
////        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
////        StrictMode.setThreadPolicy(policy)
//
//        var query = "SELECT Id, id_ModeloPrenda, id_Cliente, id_subCliente, codigoTAG, descrip, FAlta, FBaja, borrado FROM MasterPrendas "
//        if (idSubCliente > 0) {
//            query += "WHERE id_subCliente = $idSubCliente"
//        }
//
//        try {
//            DriverManager.getConnection(url, username, password).use { conn ->
//                conn.createStatement().use { stmt ->
//                    stmt.executeQuery(query).use { rs ->
//                        while (rs.next()) {
//                            val prenda = MasterPrendas(
//                                Id = rs.getInt("Id"),
//                                id_ModeloPrenda = rs.getInt("id_ModeloPrenda"),
//                                id_Cliente = rs.getInt("id_Cliente"),
//                                id_subCliente = rs.getInt("id_subCliente"),
//                                codigoTAG = if (rs.getString("codigoTAG") != null) { rs.getString("codigoTAG") } else { "" },
//                                descrip = if (rs.getString("descrip") != null) { rs.getString("descrip") } else { "" },
//                                FAlta = if (rs.getString("FAlta") != null) { rs.getString("FAlta") } else { "" },
//                                FBaja = if (rs.getString("FBaja") != null) { rs.getString("FBaja") } else { "" },
//                                borrado = rs.getBoolean("borrado")
//                            )
//                            toReturn.add(prenda)
//                        }
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getPrendas: ${e.message}")
//            throw e
//        }
//
//        return toReturn
    }

    fun getPrendaByTag(tag: String): MasterPrendas {
        var toReturn = MasterPrendas()
        try {
            val connection = HikariCPDataSource.getConnection()
            val preparedStatement = connection.prepareStatement(_getOneFromMasterPrendaByTag)
            preparedStatement.queryTimeout = QUERY_TIMEOUT_10SEC
            preparedStatement.setString(1, tag)
            val resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val item = MasterPrendas(
                    Id = resultSet.getInt("Id"),
                    id_ModeloPrenda = resultSet.getInt("id_ModeloPrenda"),
                    id_Cliente = resultSet.getInt("id_Cliente"),
                    id_subCliente = resultSet.getInt("id_subCliente"),
                    codigoTAG = if (resultSet.getString("codigoTAG") != null) { resultSet.getString("codigoTAG") } else { "" },
                    descrip = if (resultSet.getString("descrip") != null) { resultSet.getString("descrip") } else { "" },
                    FAlta = if (resultSet.getString("FAlta") != null) { resultSet.getString("FAlta") } else { "" },
                    FBaja = if (resultSet.getString("FBaja") != null) { resultSet.getString("FBaja") } else { "" },
                    borrado = resultSet.getBoolean("borrado"),
                    LastMov = resultSet.getInt("LastMov")
                )
                toReturn = item
            }
            preparedStatement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getPrendaByTag: ${e.message}")
            throw e
        }
        return toReturn
    }

    fun getPrendaClienteSubClienteByTag(tag: String): LecturaPrendaClienteSubCliente {
        var toReturn = LecturaPrendaClienteSubCliente()
        try {
            val connection = HikariCPDataSource.getConnection()
            val preparedStatement = connection.prepareStatement(_getOneFromMasterPrendaClienteSubClienteByTag)

            preparedStatement.setString(1, tag)

            val resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val item = LecturaPrendaClienteSubCliente(
                    idPrenda = resultSet.getInt("Id"),
                    nombrePrenda = resultSet.getString("NombrePrenda"),
                    talla = resultSet.getString("Talla"),
                    tag = tag,
                    isExiste = true,
                    isBorrado = resultSet.getBoolean("Borrado"),
                    idCliente = resultSet.getInt("IdCliente"),
                    nombreCliente = resultSet.getString("NombreCliente"),
                    clienteBorrado = resultSet.getBoolean("ClienteBorrado"),
                    idSubCliente = resultSet.getInt("IdSubCliente"),
                    nombreSubCliente = resultSet.getString("NombreSubCliente")+ " " + resultSet.getString("Apellido1") + " " + resultSet.getString("Apellido2"),
                    subClienteBorrado = resultSet.getBoolean("SubClienteBorrado"),
                    idModeloPrenda = resultSet.getInt("IdModeloPrenda"),
                    nombreModeloPrenda = resultSet.getString("NombreModeloPrenda"),
                    idLastMov = resultSet.getInt("IdLastMov"),
                    lastMovAntena = resultSet.getInt("LastMovAntena"),
                    lastMovFecha = if (resultSet.getString("LastMovFecha") != null) { resultSet.getString("LastMovFecha") } else { "" },
                    lastMov = resultSet.getInt(("LastMov")),
                    vestuarioSubCliente = resultSet.getString("Vestuario")
                )
                toReturn = item
            }
            preparedStatement.close()
            connection.close()
            return toReturn
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getPrendaClienteSubClienteByTag: ${e.message}")
            throw e
        }
    }

    fun getAntenas(): List<MasterTipoAntena> {
        val toReturn = mutableListOf<MasterTipoAntena>()
        try {
            val connection = HikariCPDataSource.getConnection()
            val statement = connection.createStatement()
            statement.queryTimeout = QUERY_TIMEOUT_10SEC
            val resultSet = statement.executeQuery(_getAllFromMasterTipoAntena)
            while (resultSet.next()) {
                val item = MasterTipoAntena(
                    Id = resultSet.getInt("Id"),
                    descripcion = if (resultSet.getString("descripcion") != null) { resultSet.getString("descripcion") } else { "" },
                    MostrarEnPDA = resultSet.getBoolean("MostrarEnPDA"),
                    Mostrar = resultSet.getBoolean("Mostrar"),
                    NoBorrar = resultSet.getBoolean("NoBorrar"),
                    EsBaja = resultSet.getBoolean("EsBaja"),
                    PedirUbicacion = resultSet.getBoolean("PedirUbicacion")
                )
                toReturn.add(item)
            }
            statement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getAntenas: ${e.message}")
            throw e
        }
        return toReturn
    }
    fun getAntenasPuestos(idPuesto: Int): List<MovMostrarAntenasPuesto> {
        val toReturn = mutableListOf<MovMostrarAntenasPuesto>()
        try {
            val connection = HikariCPDataSource.getConnection()
            val statement = connection.createStatement()
            statement.queryTimeout = QUERY_TIMEOUT_10SEC
            var strQuery = _getAllFromMovMostrarAntenasPuesto
            if(idPuesto > 0){
                strQuery = "$strQuery WHERE idPuesto = $idPuesto"
            }
            val resultSet = statement.executeQuery(strQuery)
            while (resultSet.next()) {
                val item = MovMostrarAntenasPuesto(
                    idPuesto = resultSet.getInt("idPuesto"),
                    idTipoAntena = resultSet.getInt("idTipoAntena")
                )
                toReturn.add(item)
            }
            statement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getAntenasPuestos: ${e.message}")
            throw e
        }
        return toReturn
    }
    fun getLastMovPrenByIdPrenda(idPrenda: Int): MovPren {
        var toReturn = MovPren()
        try {
            val connection = HikariCPDataSource.getConnection()
            val preparedStatement = connection.prepareStatement(_getLastMovPrenByIdPrenda)
            preparedStatement.queryTimeout = QUERY_TIMEOUT_10SEC
            preparedStatement.setInt(1, idPrenda)
            val resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val item = MovPren(
                    Id = resultSet.getInt("Id"),
                    id_mov = resultSet.getInt("id_mov"),
                    Id_Prenda = resultSet.getInt("Id_Prenda"),
                    Fecha = if (resultSet.getString("Fecha") != null) { resultSet.getString("Fecha") } else { "" },
                    id_Puesto = resultSet.getInt("id_Puesto"),
                    id_TipoAntena = resultSet.getInt("id_TipoAntena"),
                    idCli = resultSet.getInt("idCli"),
                    idSubCli = resultSet.getInt("idSubCli"),
                    idModeloPrenda = resultSet.getInt("idModeloPrenda")
                )
                toReturn = item
            }
            preparedStatement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getLastMovPrenByIdPrenda: ${e.message}")
            throw e
        }
        return toReturn
    }

    fun getTaquillas(): List<MasterTaquilla> {
        val toReturn = mutableListOf<MasterTaquilla>()
        try {
            val connection = HikariCPDataSource.getConnection()
            val statement = connection.createStatement()
            statement.queryTimeout = QUERY_TIMEOUT_10SEC
            val resultSet = statement.executeQuery(_getAllFromTaquilla)
            while (resultSet.next()) {
                val item = MasterTaquilla(
                    Id = resultSet.getInt("Id"),
                    Estado = resultSet.getInt("Estado"),
                    Usuario = resultSet.getInt("Usuario"),
                    Centro = resultSet.getInt("Centro"),
                    Descripcion = if (resultSet.getString("Descripcion") != null) { resultSet.getString("Descripcion") } else { "" },
                    DateLastMov = if (resultSet.getString("DateLastMov") != null) { resultSet.getString("DateLastMov") } else { "" }
                )
                toReturn.add(item)
            }
            statement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getTaquillas: ${e.message}")
            throw e
        }
        return toReturn
    }
    fun getTaquillaByDescripcion(descripcion: String): MasterTaquilla  {
        var toReturn = MasterTaquilla()
        try {
            val connection = HikariCPDataSource.getConnection()
            val preparedStatement = connection.prepareStatement(_getOneTaquillaByDescripcion)
            preparedStatement.queryTimeout = QUERY_TIMEOUT_10SEC
            preparedStatement.setString(1, descripcion)
            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                val item = MasterTaquilla(
                    Id = resultSet.getInt("Id"),
                    Estado = resultSet.getInt("Estado"),
                    Usuario = resultSet.getInt("Usuario"),
                    Centro = resultSet.getInt("Centro"),
                    Descripcion = if (resultSet.getString("Descripcion") != null) { resultSet.getString("Descripcion") } else { "" },
                    DateLastMov = if (resultSet.getString("DateLastMov") != null) { resultSet.getString("DateLastMov") } else { "" }
                )
                toReturn = item
            }
            preparedStatement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ getTaquillaByDescripcion: ${e.message}")
            throw e
        }
        return toReturn
    }

//    fun setMovPren(movPren: MovPren): Int {
//        var toReturn = 0
//        try {
//            val iDateTimeUtils = DateTimeUtils()
//            val date = iDateTimeUtils.asDate(iDateTimeUtils.getStringToLocalDate(movPren.Fecha)) as Date
//
//            val connection = HikariCPDataSource.getConnection()
//            val preparedStatement = connection.prepareStatement(_setMovPren)
//            preparedStatement.queryTimeout = QUERY_TIMEOUT_10SEC
//            preparedStatement.setInt(1, movPren.Id)
//            preparedStatement.setInt(2, movPren.Id_Prenda)
//            preparedStatement.setDate(3, date)
//            preparedStatement.setDate(4, date)
//            preparedStatement.setInt(5, movPren.id_TipoAntena)
//            preparedStatement.setInt(6, movPren.idCli)
//            preparedStatement.setInt(7, movPren.idSubCli)
//            preparedStatement.setInt(8, movPren.idModeloPrenda)
//            val resultSet = preparedStatement.executeQuery()
//            if (resultSet.next()) {
//                toReturn = resultSet.getInt(1)
//            }
//        } catch (e: Exception) {
//            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ setMovPrenIntoMovPren: ${e.message}")
//            throw e
//        }
//        return toReturn
//    }

    fun setMovPrenSP(movPren: MovPren): Boolean {
        var toReturn: Boolean
        try {
//            val iDateTimeUtils = DateTimeUtils()
//            val date = iDateTimeUtils.asDate(iDateTimeUtils.getStringToLocalDate(movPren.Fecha)) as Date

            val connection = HikariCPDataSource.getConnection()
            val preparedStatement = connection.prepareCall("{? = call spCreaMovimientoPrenda(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")
            preparedStatement.registerOutParameter(1, Types.BIT)
            preparedStatement.setInt(2, 0) //@idMov
            preparedStatement.setInt(3, movPren.Id_Prenda) //@idPrendaLeida
            preparedStatement.setInt(4, movPren.id_Puesto) //@idPuesto
            preparedStatement.setInt(5, movPren.id_TipoAntena) //@idTipoAntena
            preparedStatement.setInt(6, 0) //@idOperario
            preparedStatement.setString(7, movPren.Obser) //@Obser
            preparedStatement.setInt(8, 0) //@NumeroLote
            preparedStatement.setInt(9, 0) //@AlbaranLavander
            preparedStatement.setInt(10, movPren.idModeloPrenda) //@idModeloPrenda
            preparedStatement.setString(11, movPren.talla) //@Talla
            preparedStatement.setInt(12, 0) //@Nueva
            preparedStatement.setInt(13, 0) //@movimientoSalidaAlmacen
            preparedStatement.setInt(14, movPren.idCli) //@idNuevoCliente
            preparedStatement.setInt(15, movPren.idSubCli) //@IdNuevoSubCliente
            preparedStatement.setInt(16, 0) //@movimientoEntradaAlmacen
            preparedStatement.execute()
            toReturn = preparedStatement.getBoolean(1)
            preparedStatement.close()
            connection.close()
        } catch (e: Exception) {
            println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+ setSPMovPren: ${e.message}")
            throw e
        }
        return toReturn
    }
}