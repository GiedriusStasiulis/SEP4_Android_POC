using Microsoft.AspNetCore.Mvc;
using TemperatureAPI_Test.DataSourceService;

namespace TemperatureAPI_Test.Controllers
{
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
        public ActionResult<string> Get()
        {
            return _iDataSource.GetJson();
        }
    }
}
