using System;

public class TarjetaBancariaDTO
{
    public long Id { get; set; }
    public long NumeroTarjeta { get; set; }
    public DateTime FechaCaducidad { get; set; }
    public string TitularTarjeta { get; set; }
    public string EmisorTarjeta { get; set; }
    public int Cvv { get; set; }
    public string ImgTarjeta { get; set; }

}