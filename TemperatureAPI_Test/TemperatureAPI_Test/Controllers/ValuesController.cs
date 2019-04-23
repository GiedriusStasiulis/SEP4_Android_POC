using Microsoft.AspNetCore.Cors;
using Microsoft.AspNetCore.Mvc;
using TemperatureAPI_Test.DataSourceService;

namespace TemperatureAPI_Test.Controllers
{
    [EnableCors("MyCorsPolicy")]
    [Route("api/[controller]")]    
    [ApiController]
    public class ValuesController : ControllerBase
    {
        private readonly IDataSource _iDataSource;

        public ValuesController(IDataSource iDataSource)
        {
            _iDataSource = iDataSource;
        }
                
        [HttpGet]
        [EnableCors("MyCorsPolicy")]
        public ActionResult<string> Get()
        {
            return _iDataSource.GetJson();
        }
    }
}
