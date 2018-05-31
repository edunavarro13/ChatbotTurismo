using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CentralDuack
{
    public partial class Agregar : Form
    {
        public Agregar()
        {
            InitializeComponent();
        }

        private void inshotel_Click(object sender, EventArgs e)
        {
            try
            {
                if (nombreBox.Text != "" && direccionBox.Text != "" && latitudBox.Text != "" 
                    && longitudBox.Text != "" && localidadBox.Text != "" && provinciaBox.Text != "" 
                    && paisBox.Text != "" && telefonoBox.Text != "" && idiomaBox.Text != "")
                {
                    error.Visible = false;

                    // ---- Lectura ------
                    string sUrlRequest = "http://localhost:61163/api/Hoteles";
                    var json = new WebClient().DownloadString(sUrlRequest);

                    List<Hotel> tmp = JsonConvert.DeserializeObject<List<Hotel>>(json);
                    int idSiguiente = tmp[tmp.Count - 1].Id + 1;
                    String telef = null;
                    String web = null;
                    if (telefonoBox.Text != "")
                        telef = telefonoBox.Text;
                    if (urlBox.Text != "")
                        web = urlBox.Text;

                    // ------- POST ---------
                    var hot = new Hotel
                    {
                        Id = idSiguiente,
                        nombre = nombreBox.Text,
                        direccion = direccionBox.Text,
                        latitud = Convert.ToDouble(latitudBox.Text),
                        longitud = Convert.ToDouble(longitudBox.Text),
                        localidad = localidadBox.Text,
                        provincia = provinciaBox.Text,
                        pais = paisBox.Text,
                        precio = Convert.ToDouble(telefonoBox.Text),
                        idioma = idiomaBox.Text,
                        foto = null,
                        telefono = telef,
                        url = web
                    };

                    string json_pres = JsonConvert.SerializeObject(hot, Formatting.Indented);

                    var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://localhost:61163/api/Hoteles");
                    httpWebRequest.ContentType = "application/json";
                    httpWebRequest.Method = "POST";

                    using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
                    {
                        streamWriter.Write(json_pres);
                        streamWriter.Flush();
                        streamWriter.Close();

                        var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
                        using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
                        {
                            var result = streamReader.ReadToEnd();
                        }
                    }

                    String mens = "El hotel se ha añadido con éxito.";
                    // Display a message box asking users if they
                    // want to exit the application.
                    if (MessageBox.Show(mens, "Éxito",
                          MessageBoxButtons.OK, MessageBoxIcon.Question)
                          == DialogResult.OK)
                    {
                        this.Close();
                    }
                }
                else
                {
                    error.Text = "ERROR: Los cuadros de texto que no están en azul no pueden estar vacíos.";
                    error.Visible = true;
                }
            }
            catch (Exception ex)
            {
                error.Text = "ERROR: " + ex.ToString();
                error.Visible = true;
                //resultados.Text = ex.ToString();
            }
        }
    }

    public class Hotel
    {
        public int Id { get; set; }
        public String nombre { get; set; }
        public double latitud { get; set; }
        public double longitud { get; set; }
        public String direccion { get; set; }
        public String localidad { get; set; }
        public String provincia { get; set; }
        public String pais { get; set; }
        public double precio { get; set; }
        public String idioma { get; set; }
        public String foto { get; set; }
        public String telefono { get; set; }
        public String url { get; set; }
    }
}
