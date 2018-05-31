using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using BaseDDuack;

namespace BaseDDuack.Controllers
{
    public class HotelesController : ApiController
    {
        private BDDuackEntities db = new BDDuackEntities();

        // GET: api/Hoteles
        public IQueryable<Hoteles> GetHoteles()
        {
            return db.Hoteles;
        }

        // GET: api/Hoteles/5
        [ResponseType(typeof(Hoteles))]
        public IHttpActionResult GetHoteles(int id)
        {
            Hoteles hoteles = db.Hoteles.Find(id);
            if (hoteles == null)
            {
                return NotFound();
            }

            return Ok(hoteles);
        }

        // PUT: api/Hoteles/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutHoteles(int id, Hoteles hoteles)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != hoteles.Id)
            {
                return BadRequest();
            }

            db.Entry(hoteles).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!HotelesExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Hoteles
        [ResponseType(typeof(Hoteles))]
        public IHttpActionResult PostHoteles(Hoteles hoteles)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Hoteles.Add(hoteles);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (HotelesExists(hoteles.Id))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = hoteles.Id }, hoteles);
        }

        // DELETE: api/Hoteles/5
        [ResponseType(typeof(Hoteles))]
        public IHttpActionResult DeleteHoteles(int id)
        {
            Hoteles hoteles = db.Hoteles.Find(id);
            if (hoteles == null)
            {
                return NotFound();
            }

            db.Hoteles.Remove(hoteles);
            db.SaveChanges();

            return Ok(hoteles);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool HotelesExists(int id)
        {
            return db.Hoteles.Count(e => e.Id == id) > 0;
        }
    }
}