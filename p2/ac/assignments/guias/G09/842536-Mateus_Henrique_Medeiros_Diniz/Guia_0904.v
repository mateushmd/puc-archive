/*
	Guia_0904.v
	842536 - Mateus Henrique Medeiros Diniz
*/

`include "clock.v"

module Guia_0904;
	wire clock;
	
	clock clk ( clock );
	
	wire p;
	
	pulse pulse ( p, clock );
	
	initial begin
		$dumpfile ( "Guia_0904.vcd" );
		$dumpvars ( 1, clock, p );
		#376 $finish;
	end
endmodule

module pulse ( signal, clock );
	input clock;
	output signal;	
	reg signal;
	reg counter;
	
	initial begin
		counter = 0;
		signal = 0;
	end
	
	always @ ( negedge clock )
	begin
		counter <= ~counter;
		if(counter == 1) begin
			signal <= ~signal;
		end
	end
endmodule
